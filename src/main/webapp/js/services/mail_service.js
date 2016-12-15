/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


angular.module("safedeals.services.mail", []);
angular.module("safedeals.services.mail")
        .factory('MailService', function ($resource, restRoot) {
            return $resource(restRoot + '/mail_service/:id', {'id': '@id'}, {
                
                'sendEmail': {
                    'method': 'POST',
                    'url': restRoot + '/mail_service/send_mail',
                    'params': {
                        'mailId': '@mailId'
                    }
                }
            });
        });


