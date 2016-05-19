package aws.buddy

class ToolsController extends AbstractApiController {

    Ec2Service ec2Service

    def refresh() {
        renderJson(ApiResponse.getOk([list: ec2Service.get()]))
    }
}
