angular.module('cycleApp').controller('CycleController', ['$scope', 'CycleService', function($scope, CycleService) {
    var self = this;
    self.subdivisions=[];
    self.equipments=[];
    self.repair_cycle = [];
    getAllEquipments();

    function getAllEquipments(){
        CycleService.getAllEquipments()
            .then(
                function(d) {
                    console.log('Sub = ' + d);
                    self.equipments = d;
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
                    console.log('AAAA = ' + equipmentId);
                    self.repair_cycle.push(d);
                },
                function(errResponse){
                    console.error('Error while fetching getRepairCycleByEquipment');
                }
            )
    }
}]);