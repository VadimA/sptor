var App = angular.module('userApp',[]);

angular.module('userApp').factory('UserService', ['$http', '$q', function($http, $q){

    var REST_SERVICE_URI = '/users/';

    var factory = {
        fetchAllUsers: fetchAllUsers,
        deleteUser: deleteUser,
        addUser: addUser
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

    function addUser(user){
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI, user)
            .then(
                function (response) {
                    deferred.resolve(response.data);
                },
                function (errResponse) {
                    console.error('Error while adding User');
                    deferred.reject(errResponse);
                }
            );
        return deferred.promise;
    }
}]);