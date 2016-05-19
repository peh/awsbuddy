// Added by the Spring Security Core plugin:
grails.plugin.springsecurity.userLookup.userDomainClassName = 'aws.buddy.User'
grails.plugin.springsecurity.userLookup.authorityJoinClassName = 'aws.buddy.UserRole'
grails.plugin.springsecurity.authority.className = 'aws.buddy.Role'
grails.plugin.springsecurity.password.algorithm = 'bcrypt'
grails.plugin.springsecurity.rejectIfNoRule = false
grails.plugin.springsecurity.fii.rejectPublicInvocations = false
grails.plugin.springsecurity.logout.postOnly = false
grails.plugin.springsecurity.securityConfigType = "InterceptUrlMap"
grails.plugin.springsecurity.interceptUrlMap = [
        [pattern: '/**', access: ['permitAll']],
        [pattern: '/**/js/**', access: ['permitAll']],
        [pattern: '/**/css/**', access: ['permitAll']],
        [pattern: '/**/images/**', access: ['permitAll']],
        [pattern: '/**/font/**', access: ['permitAll']],
        [pattern: '/**/favicon.ico', access: ['permitAll']]
]
grails.plugin.springsecurity.rest.login.endpointUrl = '/api/users/login'
grails.plugin.springsecurity.rest.logout.endpointUrl = '/api/users/logout'
grails.plugin.springsecurity.rest.token.validation.endpointUrl = '/api/users/validate'
grails.plugin.springsecurity.rest.token.storage.useJwt = false
grails.plugin.springsecurity.rest.token.storage.useRedis = true
grails.plugin.springsecurity.rest.token.storage.redis.expiration = 8640000

grails.plugin.springsecurity.filterChain.chainMap = [
        //Stateless chain
        [
                pattern: '/api/**',
                filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter,-exceptionTranslationFilter,-authenticationProcessingFilter,-securityContextPersistenceFilter,-rememberMeAuthenticationFilter'
        ],

        //Traditional chain
        [
                pattern: '/**',
                filters: 'JOINED_FILTERS,-restTokenValidationFilter,-restExceptionTranslationFilter'
        ]
]

grails.gorm.default.mapping = {
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentDateTime, class: org.joda.time.DateTime
    "user-type" type: org.jadira.usertype.dateandtime.joda.PersistentLocalDate, class: org.joda.time.LocalDate
}
