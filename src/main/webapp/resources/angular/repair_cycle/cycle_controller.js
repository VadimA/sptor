angular.module('cycleApp').controller('CycleController', ['$scope', 'CycleService', function($scope, CycleService) {
    var self = this;
    self.subdivisions=[];
    self.equipments=[];
    self.repair_cycle = [];
    getAllSubdivision();

    function getAllSubdivision(){
        CycleService.getAllSubdivision()
            .then(
                function(d) {
                    self.subdivisions = d;
                },
                function(errResponse){
                    console.error('Error while fetching Equipments');
                }
            );
    }

    function getRepairCycleByEquipment(equipmentId){
        CycleService.getRepairCycleByEquipment(equipmentId)
            .then(
                function (d) {
                    self.repair_cycle.push(d);
                    console.log('Sub = ' + d[0]);
                },
                function(errResponse){
                    console.error('Error while fetching getRepairCycleByEquipment');
                }
            )
    }
}]);