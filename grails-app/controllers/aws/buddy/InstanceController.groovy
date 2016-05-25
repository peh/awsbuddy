package aws.buddy

class InstanceController extends AbstractApiController {
    InstanceService instanceService

    def get() {
        withInstance { Instance instance ->
            def metrics = Metric.findAllByInstance(instance, [max: 20, sort: 'forTime', order: 'desc'])
            renderJson(ApiResponse.getOk([instance: instance, metrics: metrics]))
        }
    }

    def list() {
        Filter filter = Filter.get(params.getLong('filter')) ?: new Filter(params)
        List instances = instanceService.list(filter, params)
        renderJson(ApiResponse.getOk([instances: instances]))
    }

    void withInstance(Closure c) {
        Instance instance = Instance.get(params.getLong('id', -1L))
        if (instance) {
            c.call instance
        } else {
            renderJson(ApiResponse.notFound)
        }
    }
}
