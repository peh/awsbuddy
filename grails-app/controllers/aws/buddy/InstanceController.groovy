package aws.buddy

class InstanceController extends AbstractApiController {

    def get() {
        withInstance { Instance instance ->
            def metrics = Metric.findAllByInstance(instance, [max: 20, sort: 'forTime', order: 'desc'])
            renderJson(ApiResponse.getOk([instance: instance, metrics: metrics]))
        }
    }

    def list() {
        renderJson(ApiResponse.getOk([instances: Instance.list(params)]))
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
