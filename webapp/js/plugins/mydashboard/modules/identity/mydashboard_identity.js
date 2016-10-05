
function validate_preferredUsername(elem) {
    $.ajax({
        url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_preferredUsername&preferredUsername="+elem.value,
        type: "GET",
        dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
        });
    

}

function validate_lastName(elem) {
    $.ajax({
        url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_lastName&lastName="+elem.value,
        type: "GET",
        dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
        });
    

}

function validate_firstname(elem) {
    $.ajax({
        url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_firstname&firstname="+elem.value,
        type: "GET",
        dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
        });
    

}

function validate_birthdate(elem) {
    $.ajax({
        url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_birthdate&birthdate="+elem.value,
        type: "GET",
        dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
        });
    

}

function validate_birthplace(elem) {
    $.ajax({
        url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_birthplace&birthplace="+elem.value,
        type: "GET",
        dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
        });
    

}

function validate_birthcountry(elem) {
    $.ajax({
        url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_birthcountry&birthcountry="+elem.value,
        type: "GET",
        dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
        });
    

}

function validate_address_detail(elem) {
$.ajax({
    url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_addressdetail&address_detail="+elem.value,
    type: "GET",
    dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
    });


}

function validate_address_postalcode(elem) {
$.ajax({
    url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_addressPostalcode&address_postalcode="+elem.value,
    type: "GET",
    dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
    });


}

function validate_address_city(elem) {
$.ajax({
    url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_addressCity&address_city="+elem.value,
    type: "GET",
    dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
    });


}

function validate_phone(elem) {
$.ajax({
    url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_phone&phone="+elem.value,
    type: "GET",
    dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
    });


}

function validate_mobilephone(elem) {
$.ajax({
    url: "jsp/site/Portal.jsp?page=mydashboardIdentity&view=validate_mobilephone&mobilePhone.mobilePhoneNumber="+elem.value,
    type: "GET",
    dataType : "html",
        success: function( response ) {
        	if (response != "") {
			$(elem).parents(".form-group").addClass("has-error has-feedback");
			$(elem).focus();
	        var helpblock = $(elem).parents(".form-group").children("p.help-block");
	        helpblock.children("p").remove();
	        helpblock.append("<p>"+response+"</p>");
			helpblock.addClass("alert alert-danger");
			helpblock.show();
        	}else{
			parent.hide();
			$(elem).parents(".form-group").removeClass("has-error has-feedback");
			$(elem).parents(".form-group").children("p.help-block").hide();		        
			}
        }
    });


}

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

