<%-- 
    Document   : index
    Created on : 2 Mar, 2016, 2:11:35 PM
    Author     : ruchita
    Modified By: Aniket
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html ng-app="safedeals">
    <head>
        <base href="/safe-deals/" target="_blank">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
        <title>Safe Deals</title>

        <!--Stylesheet-->
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/3.3.5/css/bootstrap.min.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/modal-override.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/corporate_site.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/angular-bootstrap-lightbox.css"/>
        <!--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/flexslider.css"/>-->

        <!--Libraries-->
        <script src="${pageContext.request.contextPath}/webjars/jquery/1.12.0/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/angularjs/1.5.3/angular.min.js"></script>  
        <script src="${pageContext.request.contextPath}/webjars/nervgh-angular-file-upload/2.1.1/angular-file-upload.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/angular-animate/1.5.3/angular-animate.js"></script>  
        <script src="${pageContext.request.contextPath}/webjars/angular-resource/1.2.28/angular-resource.js"></script>  
        <script src="${pageContext.request.contextPath}/webjars/bootstrap/3.3.5/js/bootstrap.min.js"></script>  
        <script src="${pageContext.request.contextPath}/webjars/angular-ui-router/0.2.15/angular-ui-router.js"></script>  
        <script src="${pageContext.request.contextPath}/webjars/angular-ui-bootstrap/1.2.5/ui-bootstrap-tpls.min.js"></script>
        <script src="${pageContext.request.contextPath}/webjars/underscorejs/1.5.1/underscore.min.js"></script>        
        <!--<script src="${pageContext.request.contextPath}/webjars/jquery/2.1.4/jquery.min.js"></script>--> 
        <script src="${pageContext.request.contextPath}/js/lib/angular-google-map.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/angular-simple-logger.js"></script>        
        <script src="https://maps.googleapis.com/maps/api/js?libraries=geometry,places&region=IN&key=AIzaSyBEYDdJx8BB-fQa_H2qKoUO84oUrH8BFQE"></script>
        <script type="text/javascript" src="//translate.google.com/translate_a/element.js?cb=googleTranslateElementInit"></script>
        <script src="${pageContext.request.contextPath}/js/lib/fusioncharts.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/fusioncharts.charts.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/fusioncharts.theme.fint.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/ng-google-chart.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/calcumateemi.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/googleMap.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/jquery.flexslider-min.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/scripts.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/wow.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/lodash.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/angular-bootstrap-lightbox.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/ngComboDatePicker.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/lib/ngComboDatePicker.js"></script>
        <!--<script src="${pageContext.request.contextPath}/js/directives/scroll.js"></script>-->
        <!--<script src="https://rawgithub.com/eligrey/FileSaver.js/master/FileSaver.js" type="text/javascript"></script>-->
        <!--        <script>
                    $(document).ready(function () {
                        $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        
                            var data-ui-sref = $(e.target).attr('data-ui-sref');
        
                            var $curr = $(".checkout-bar  a[data-ui-sref='" + data-ui-sref + "']").parent();
                            console.log("$curr", $curr.attr("id"));
        
                            $('.checkout-bar li').removeClass();
        
                            if ($curr.attr("id") === "lastTab") {
                                $curr.addClass("visited");
                            }
                            else {
                                $curr.addClass("active");
                            }
        
                            $curr.prevAll().addClass("visited");
        
        
                        });
                    });
                </script>-->

        <!--Constants-->
        <script>
            angular.module("safedeals.constants", [])
                    .constant('contextPath', '${pageContext.request.contextPath}')
                    .constant('restRoot', '${pageContext.request.contextPath}/rest')
                    .constant('templateRoot', '${pageContext.request.contextPath}/templates')
                    .constant('imageRoot', '${pageContext.request.contextPath}/images')
                    .constant('paginationLimit', 5);
        </script>

        <!--JavaScript-->
        <script src="${pageContext.request.contextPath}/js/app.js"></script>
        <script src="${pageContext.request.contextPath}/js/auth.js"></script>
        <script src="${pageContext.request.contextPath}/js/filters.js"></script>
        <script src="${pageContext.request.contextPath}/js/directives/datetime_picker.js"></script>
        <!--<script src="${pageContext.request.contextPath}/js/directives/scroll.js"></script>-->
        <!-- Directive -->
        <script src="${pageContext.request.contextPath}/js/directives/bank_add.js"></script>
        <!--states-->
        <script src="${pageContext.request.contextPath}/js/states.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/admin.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/corporate_site.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/portal.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/guidelines.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/intro.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/location.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/location_master.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/property_master.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/property.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/project_master.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/faqs.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/thanku.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/help.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/final_deal.js"></script>
        <!---masters script--->
        <script src="${pageContext.request.contextPath}/js/states/branch.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/country.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/state.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/city.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/bank.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/amenity.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/amenity_detail.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/hospital.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/ready_reckoner.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/raw_ready_reckoner.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/raw_market_price.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/salary_range.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/safedeal_zone.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/workplace_area.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/workplace_category.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/workplace_category_detail.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/user.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/price_range.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/location_type.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/location_category.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/market_price.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/business_associate.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/franchise.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/property_type.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/video.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/agency.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/agent.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/builder.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/testimonial.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/event.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/team.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/amenity_code.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/private_amenities.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/transportation.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/road.js"></script>
        <script src="${pageContext.request.contextPath}/js/states/image.js"></script>

        <!--Services-->
        <script src="${pageContext.request.contextPath}/js/services/branch_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/country_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/state_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/city_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/bank_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/location_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/amenity_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/amenity_detail_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/income_slab_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/property_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/co_ordinate_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/school_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/hospital_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/price_range_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/guidelines_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/project_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/mall_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/transportation_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/ready_reckoner_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/raw_ready_reckoner_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/raw_market_price_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/salary_range_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/safedeal_zone_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/workplace_area_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/workplace_category_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/workplace_category_detail_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/user_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/location_type_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/location_category_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/market_price_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/business_associate_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/franchise_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/property_type_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/video_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/agency_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/agent_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/builder_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/testimonial_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/event_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/team_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/enquiry_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/amenity_code_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/private_amenities_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/road_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/mail_service.js"></script>
        <script src="${pageContext.request.contextPath}/js/services/image_service.js"></script>

    </head>
    <body>
        <div id="parrentDiv" class="bg-city-spcl" data-ui-view></div>
    </body>
</html>
