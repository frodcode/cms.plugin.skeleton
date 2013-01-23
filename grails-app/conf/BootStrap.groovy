import org.codehaus.groovy.grails.commons.ApplicationAttributes

import domain.routing.*

class BootStrap {

	def init = { servletContext ->
		Host defaultHost = new Host(protocol : 'http', domain: 'localhost', port: '8080', domainUrlPart: '/Skeleton')
		defaultHost.save(failOnError:true)
		def ctx = servletContext.getAttribute(ApplicationAttributes.APPLICATION_CONTEXT)
		def articleMCInstance = ctx.'article.control.ArticleModuleControl'
		def routingMCInstance = ctx.'routing.control.RoutingModuleControl';

		def routingData = RoutingGrailsPlugin.loadFixtures(ctx, defaultHost)
		def articleData = ArticleGrailsPlugin.loadFixtures(ctx, defaultHost, routingData.moduleControls.routingMc, routingData.pages.homepage, routingData.pages.adminHomepage)

		ArticleGrailsPlugin.loadExampleData(
				ctx,
				routingMCInstance.getSingleton(articleMCInstance.frontListSlug),
				routingMCInstance.getSingletonType(articleMCInstance.frontDetailSlug),
				routingMCInstance.getSingleton(articleMCInstance.adminListSlug),
				routingMCInstance.getSingletonType(articleMCInstance.adminUpdateSlug))
	}
	def destroy = {
	}
}
