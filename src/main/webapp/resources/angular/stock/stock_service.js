var App = angular.module('stockApp', ["ngTable"]);

angular.module('stockApp').factory('StockService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = '/spare/all';

    var factory = {
        getAllSpares: getAllSpares,
    };

    return factory;

    function getAllSpares() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching spares');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);