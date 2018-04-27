angular.module('cycleApp').controller('CycleController', ['$scope', 'CycleService', function($scope, CycleService) {
    var self = this;
    self.subdivisions=[];
    self.equipments=[];
    getAllSubdivision();

    function getAllSubdivision(){
        CycleService.getAllSubdivision()
            .then(
                function(d) {
                    self.subdivisions = d;
                    console.log('Sub = ' + d[0].equipments_sub[0].equipmentName);
                },
                function(errResponse){
                    console.error('Error while fetching Equipments');
                }
            );
    }
}]);