package aws.buddy

class FilterController extends AbstractApiController {

    def get() {
        withFilter { Filter filter ->
            renderJson(ApiResponse.getOk([filter: filter]))
        }
    }

    def list() {
        renderJson(ApiResponse.getOk([filters: Filter.list()]))
    }

    def create() {
        Filter filter = new Filter(params).save()
        renderJson(ApiResponse.getOk([filter: filter]))
    }

    def edit() {
        withFilter { Filter filter ->
            filter.setProperties(params)
            renderJson(ApiResponse.getOk([filter: filter]))
        }
    }

    void withFilter(Closure c) {
        Filter instance = Filter.get(params.getLong('id', -1L))
        if (instance) {
            c.call instance
        } else {
            renderJson(ApiResponse.notFound)
        }
    }
}
