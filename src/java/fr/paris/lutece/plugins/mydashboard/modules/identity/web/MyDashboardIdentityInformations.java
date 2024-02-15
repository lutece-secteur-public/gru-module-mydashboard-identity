package fr.paris.lutece.plugins.mydashboard.modules.identity.web;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import fr.paris.lutece.portal.service.i18n.I18nService;

public class MyDashboardIdentityInformations extends AbstractMyDashboardIdentity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final String DASHBOARD_COMPONENT_ID = "mydashboard-identity.identityInformations";
    private static final String MESSAGE_DASHBOARD_COMPONENT_DESCRIPTION = "module.mydashboard.identity.dashboard.indentityInfos.description";
    private static final String TEMPLATE_DASHBOARD_COMPONENT = "/skin/plugins/mydashboard/modules/identity/dashboard/identity_infos_component.html";

	@Override
	public String getDashboardData( HttpServletRequest request )
	{
		_strTemplate = TEMPLATE_DASHBOARD_COMPONENT;
		return getData( request );
	}
	
	@Override
    public String getComponentDescription( Locale locale )
    {
        return I18nService.getLocalizedString( MESSAGE_DASHBOARD_COMPONENT_DESCRIPTION, locale );
    }

    @Override
    public String getComponentId( )
    {
        return DASHBOARD_COMPONENT_ID;
    }
}
