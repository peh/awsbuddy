package aws.buddy

import com.amazonaws.regions.Region
import com.amazonaws.regions.Regions
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient
import com.amazonaws.services.cloudwatch.model.Datapoint
import com.amazonaws.services.cloudwatch.model.Dimension
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest
import com.amazonaws.services.ec2.AmazonEC2Client
import grails.transaction.Transactional
import grails.util.Environment
import org.joda.time.DateTime

import java.math.RoundingMode

@Transactional
class Ec2Service {

    AmazonEC2Client ec2Client
    AmazonCloudWatchClient cloudWatchClient
    InstanceService instanceService
    def grailsApplication

    def get() {
        def instances = []
        EC2?.describeInstances()?.reservations?.instances?.flatten()?.each { com.amazonaws.services.ec2.model.Instance awsInstance ->

            Instance instance = Instance.findByIdentifier(awsInstance.instanceId)
            if (!instance) {
                instance = instanceService.createFromAws(awsInstance)
            }
            instance.state = instanceService.getStateFromAws(awsInstance.state)
            instance.lastSeen = instance.state == Instance.State.RUNNING ? DateTime.now() : instance.lastSeen
            instances << instance
        }
        instances.each { Instance instance ->
            getCpuMetrics(instance)
        }
        instances
    }

    def getCpuMetrics(Instance instance) {
        if (instance.lastMetricCheck.isBefore(DateTime.now().minusMinutes(1)) || Environment.developmentMode) {
            def req = new GetMetricStatisticsRequest().withStartTime(instance.lastMetricCheck.minusMinutes(30).toDate())
                    .withNamespace("AWS/EC2")
                    .withPeriod(60)
                    .withDimensions(new Dimension().withName("InstanceId").withValue(instance.identifier))
                    .withMetricName("CPUUtilization")
                    .withStatistics("Average", "Maximum", "Minimum", "Sum", "SampleCount")
                    .withEndTime(new Date())
            instance.lastMetricCheck = DateTime.now()
            cloudWatch.getMetricStatistics(req).datapoints.each { Datapoint dp ->
                DateTime forTime = new DateTime(dp.timestamp)
                Metric metric = Metric.findOrCreateByInstanceAndForTime(instance, forTime)
                if (!metric.id) {
                    metric.type = Metric.Type.CPU
                    metric.unit = dp.unit
                    metric.maximum = BigDecimal.valueOf(dp.maximum).setScale(2, RoundingMode.HALF_EVEN)
                    metric.minimum = BigDecimal.valueOf(dp.minimum).setScale(2, RoundingMode.HALF_EVEN)
                    metric.average = BigDecimal.valueOf(dp.average).setScale(2, RoundingMode.HALF_EVEN)
                    metric.sum = BigDecimal.valueOf(dp.sum).setScale(2, RoundingMode.HALF_EVEN)
                    if (metric.validate()) {
                        metric.save()
                    } else {
                        log.error "could not save metric, cause: $metric.errors.allErrors"
                    }
                }
            }
        }
    }

    AmazonEC2Client getEC2() {
        if (!ec2Client) {
            ec2Client = new AmazonEC2Client()
            ec2Client.setRegion(region)
        }
        ec2Client
    }

    AmazonCloudWatchClient getCloudWatch() {
        if (!cloudWatchClient) {
            cloudWatchClient = new AmazonCloudWatchClient()
            cloudWatchClient.setRegion(region)
        }
        cloudWatchClient
    }


    private Region getRegion() {
        //TODO implement configurable region
        Region.getRegion(config.region ? Regions.fromName(config.region) : Regions.EU_CENTRAL_1)
    }

    def getConfig() {
        grailsApplication.config.awsbuddy
    }

}
