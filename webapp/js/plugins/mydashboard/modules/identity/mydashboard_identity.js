

$('#accept_receive_survey').change(function(){
    var isChecked = $(this).is(':checked');
    $.ajax({
        url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=update_acceptSurvey&bAccept="+isChecked,
        type: "GET",
        dataType : "html",
        success: function( response ) {}
        });
    
});

$('#accept_receive_news').change(function(){
    var isChecked = $(this).is(':checked');
    $.ajax({
        url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=update_acceptNews&bAccept="+isChecked,
        type: "GET",
        dataType : "html",
        success: function( response ) {}
        });
});

