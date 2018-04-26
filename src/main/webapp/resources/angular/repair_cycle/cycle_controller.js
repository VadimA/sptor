angular.module('planApp').controller('PlanController', ['$scope', 'PlanService', 'NgTableParams', function($scope, PlanService, NgTableParams) {
    var self = this;
    self.equipment={equipment:null, typeOfMaintenance:null, lastDateOfMaintenance:null, nextDateOfMaintenance:null,
        current_working_hours: null, last_working_hours: null};
    self.equipments=[];
    getEquipmentsOnMaintenance();
    var data = null;
    function getEquipmentsOnMaintenance(){
        PlanService.getEquipmentsOnMaintenance()
            .then(
                function(d) {
                    self.equipments = d;
                    data = d;
                    $scope.tableParams.reload();
                    self.tableParams.reload();
                },
                function(errResponse){
                    console.error('Error while fetching Equipments');
                }
            );
    }

    $scope.tableParams = new NgTableParams({
        page: 1,            // show first page
        count: 10           // count per page
    }, {
        total:  self.equipments.length, // length of data
        getData: function($defer, params) {
            params.total(self.equipments.length);
            $defer.resolve(data.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            //$defer.resolve($scope.dataset.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        }
    });

    //$scope.tableParams = new NgTableParams({ count: 5 }, { counts: [5, 10, 20], dataset: data});
    //self.tableParams = new NgTableParams({ count: 5 }, { counts: [5, 10, 20], dataset: data});
    //self.selectedPageSizes = self.tableParams.settings().counts;
    //self.availablePageSizes = [5, 10, 15, 20, 25, 30, 40, 50, 100];
    self.changePage = changePage;
    self.changePageSize = changePageSize;
    self.changePageSizes = changePageSizes;

    function changePage(nextPage){
        self.tableParams.page(nextPage);
    }

    function changePageSize(newSize){
        self.tableParams.count(newSize);
    }

    function changePageSizes(newSizes){
        // ensure that the current page size is one of the options
        if (newSizes.indexOf(self.tableParams.count()) === -1) {
            newSizes.push(self.tableParams.count());
            newSizes.sort();
        }
        self.tableParams.settings({ counts: newSizes});
    }
}]);