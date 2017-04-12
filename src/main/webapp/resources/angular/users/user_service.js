var App = angular.module('userApp',[]);

angular.module('userApp').factory('UserService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = 'https://localhost:8081/users/';

    var factory = {
        fetchAllUsers: fetchAllUsers,
        deleteUser: deleteUser
    };

    return factory;

    function fetchAllUsers() {
        var deferred = $q.defer();
        $http.get(REST_SERVICE_URI + "all/")
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function(errResponse){
                    console.error('Error while fetching Users');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }

    function deleteUser(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI + id)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while delete User');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);