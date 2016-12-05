angular.module('safedeals.filters', [])
    .filter('getById', function () {
            return function (input, id) {
                console.log("input in filter", input);
                console.log("id in filter",id);
                var i = 0, len = input.length;
                for (; i < len; i++) {
                    if (+input[i].id === +id) {
                        return input[i];
                    }
                }
                return null;
            };
        })
    .filter('monthName', function () {
            return function (monthName) {
                        console.log("monthName",monthName);
                switch (monthName) {
                    case 1 :
                        //console.log("monthNamecase",monthName);
                        return "January";
                        break;
                    case 2 :
                        return "February";
                        break;
                    case 3:
                        return "March";
                        break;
                    case 4:
                        return "April";
                        break;
                    case 5:
                        return "May";
                        break;
                    case 6:
                        return "June";
                        break;
                    case 7:
                        return "July";
                        break;
                    case 8:
                        return "August";
                        break;
                    case 9:
                        return "September";
                        break;
                    case 10:
                        return "October";
                        break;
                    case 11:
                        return "November";
                        break;
                    case 12:
                        return "December";
                        break;
                    default :
                        return "Invalid Month";
                }
            };
        });