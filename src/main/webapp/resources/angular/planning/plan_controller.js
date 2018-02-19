angular.module('planApp').controller('PlanController', ['$scope', 'PlanService', 'NgTableParams', function($scope, PlanService, NgTableParams) {
    var self = this;
    self.equipment={equipment:null, typeOfMaintenance:null, lastDateOfMaintenance:null, nextDateOfMaintenance:null,
        current_working_hours: null, last_working_hours: null};
    self.equipments=[];
    getEquipmentsOnMaintenance();

    function getEquipmentsOnMaintenance(){
        PlanService.getEquipmentsOnMaintenance()
            .then(
                function(d) {
                    self.equipments = d;
                    var data = d;
                    $scope.tableParams = new NgTableParams({}, { dataset: data});
                },
                function(errResponse){
                    console.error('Error while fetching Equipments');
                }
            );
    }
}]);