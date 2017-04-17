angular.module('userApp').controller('UserController', ['$scope', 'UserService', function($scope, UserService) {
    var self = this;
    self.user={id:null,first_name:'',last_name:'',email:'', password: '', ssoid:''};
    self.users=[];

    self.remove=remove;
    self.reset=reset;
    self.submit=submit;
    self.edit=edit;

    fetchAllUsers();

    function fetchAllUsers(){
        UserService.fetchAllUsers()
            .then(
                function(d) {
                    self.users = d;
                },
                function(errResponse){
                    console.error('Error while fetching Users');
                }
            );
    }

    function deleteUser(id){
        UserService.deleteUser(id)
            .then(
                fetchAllUsers,
                function(errResponse){
                    console.error('Error while deleting User');
                }
            );
    }

    function addUser(user){
        UserService.addUser(user)
            .then(
                fetchAllUsers,
                function(errResponse){
                    console.error('Error while adding User');
                }
            );
    }

    function submit() {
        if(self.user.id===null){
            console.log('Saving New User', self.user);
            addUser(self.user);
        }else{
            addUser(self.user);
            console.log('User updated with id ', self.user.id);
        }
        reset();
    }

    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.users.length; i++){
            if(self.users[i].id === id) {
                self.user = angular.copy(self.users[i]);
                break;
            }
        }
    }

    function remove(id){
        console.log('id to be deleted', id);
        if(self.user.id === id) {
            reset();
        }
        deleteUser(id);
    }

    function reset(){
        self.user={id:null,first_name:'',last_name:'',email:'', password: '', ssoid:''};
    }

}]).directive('ngConfirmClick', [
    function(){
        return {
            link: function (scope, element, attr) {
                var msg = attr.ngConfirmClick || "Вы уверены?";
                var clickAction = attr.confirmedClick;
                element.bind('click',function (event) {
                    if ( window.confirm(msg) ) {
                        scope.$eval(clickAction)
                    }
                });
            }
        };
    }])
;