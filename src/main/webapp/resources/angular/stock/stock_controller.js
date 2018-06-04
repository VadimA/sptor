angular.module('stockApp').controller('StockController', ['$scope', 'StockService', 'NgTableParams', function($scope, StockService, NgTableParams) {
    var self = this;
    self.spare={spare_id: null, spare_name:null, spare_producer:null, price:null, amount_in_stock:null,
        description: null};
    self.spares=[];
    getAllSpares();
    var data = null;
    function getAllSpares(){
        StockService.getAllSpares()
            .then(
                function(d) {
                    self.spares = d;
                    data = d;
                },
                function(errResponse){
                    console.error('Error while fetching Spares');
                }
            );
    }
}]);