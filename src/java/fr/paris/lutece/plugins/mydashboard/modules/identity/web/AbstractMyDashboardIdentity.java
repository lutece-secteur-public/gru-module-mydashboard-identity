package fr.paris.lutece.plugins.mydashboard.modules.identity.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import fr.paris.lutece.plugins.identitystore.v3.web.rs.dto.common.IdentityDto;
import fr.paris.lutece.plugins.mydashboard.modules.identity.business.DashboardIdentity;
import fr.paris.lutece.plugins.mydashboard.modules.identity.util.DashboardIdentityUtils;
import fr.paris.lutece.plugins.mydashboard.service.MyDashboardComponent;
import fr.paris.lutece.portal.service.security.LuteceUser;
import fr.paris.lutece.portal.service.security.SecurityService;
import fr.paris.lutece.portal.service.security.UserNotSignedException;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.service.util.AppLogService;
import fr.paris.lutece.portal.web.l10n.LocaleService;
import fr.paris.lutece.util.html.HtmlTemplate;

public abstract class AbstractMyDashboardIdentity extends MyDashboardComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String MARK_IDENTITY = "identity";
	
	public String _strTemplate;
	
	private DashboardIdentity _dashboardIdentity;
	
	public String getData( HttpServletRequest request )
	{
	    LuteceUser luteceUser = SecurityService.isAuthenticationEnable( ) ? SecurityService.getInstance( ).getRegisteredUser( request ) : null;
	    
	    if ( luteceUser == null )
	    {
	        return StringUtils.EMPTY;
	    }
	    
		Map<String, Object> model = new HashMap<>( );
		
		IdentityDto identity = DashboardIdentityUtils.getInstance( ).getIdentity( luteceUser.getName( ) );
        _dashboardIdentity = DashboardIdentityUtils.getInstance( ).convertToDashboardIdentity( identity );
        
        model.put( MARK_IDENTITY, _dashboardIdentity );
		
		HtmlTemplate template = AppTemplateService.getTemplate( _strTemplate, LocaleService.getDefault( ), model );

        return template.getHtml( );
	}
}
