/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module("safedeals.services.enquiry", []);
angular.module("safedeals.services.enquiry")
        .factory('EnquiryService', function ($resource, restRoot) {
            return $resource(restRoot + '/enquiry/:id', {'id': '@id'}, {
                'findByName': {
                    'method': 'GET',
                    'url': restRoot + '/enquiry/find/name',
                    'params': {
                        'name': '@name'
                    },
                    'isArray': false
                },
                'sendSms': {
                    'method': 'POST',
                    'url': restRoot + '/enquiry/send_sms'
//                    'params': {
//                        'clientNumber': '@clientNumber'
//                    }
                }
            });
        });


