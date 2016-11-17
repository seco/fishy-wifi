package harvest

import static grails.plugin.springsecurity.SpringSecurityUtils.ifAllGranted

class ProfileCompletionInterceptor {
    def springSecurityService
    ProfileCompletionInterceptor() {
        matchAll()
            .excludes(controller: "growingSpace", action:"create")
            .excludes(controller: "allotment", action:"create")
            .excludes(controller: "otherGrowingSpace", action:"create")
            .excludes(controller: "garden", action:"create")
            .excludes(controller: "growingSpace", action:"save")
            .excludes(controller: "allotment", action:"save")
            .excludes(controller: "otherGrowingSpace", action:"save")
            .excludes(controller: "garden", action:"save")

    }

    int order = LOWEST_PRECEDENCE - 10

    boolean before() {
        if (!ifAllGranted("ROLE_ADMIN") &&
                springSecurityService != null &&
                springSecurityService.currentUser != null) {
            def growingSpace = springSecurityService.currentUser.growingSpace
            if (growingSpace == null) {
                redirect(controller:"growingSpace", action: "create")
                false
            } else {
                true
            }
        } else {
            true
        }
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
