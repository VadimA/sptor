function getList () {
    $('.tree li:has(ul)').addClass('parent_li').find(' > span').attr('title', 'Свернуть');
    $('.tree li.parent_li > span').on('click', function (e) {
        var children = $(this).parent('li.parent_li').find(' > ul > li');
        if (children.is(":visible")) {
            children.hide('fast');
            $(this).attr('title', 'Развернуть').find(' > i').addClass('icon-plus-sign').removeClass('icon-minus-sign');
        } else {
            children.show('fast');
            $(this).attr('title', 'Свернуть').find(' > i').addClass('icon-minus-sign').removeClass('icon-plus-sign');
        }
        e.stopPropagation();
    });
}

function SendGet(equipment_id) {
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/equipments/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        jQuery("#result").html("          <div class=\"col-md-3\" style=\"margin: 25px;\">\n\
            <p class=\"form-control-static\"> <b> Наименование:</b></p>\n\
            <p class=\"form-control-static\"> <b> Тип оборудования:</b></p>\n\
            <p class=\"form-control-static\"> <b> Местонахождение:</b></p>\n\
            <p class=\"form-control-static\"> <b> Инвентарный номер:</b></p>\n\
            <p class=\"form-control-static\"> <b> Год начала эксплуатации:</b></p>\n\
            <p class=\"form-control-static\"> <b> Изготовитель:</b></p>\n\
            <p class=\"form-control-static\"> <b> Описание:</b></p>\n\
          </div>\n\
\n\
          <div class=\"col-md-5\" style=\"margin: 25px;\" align=\"left\">\n\
            <p class=\"form-control-static\" id=\"eq_name\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_type\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_sub\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_inv\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_year\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_prod\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"eq_desc\"></p>\n\
          </div>");
        jQuery("#eq_name").text(data.equipmentName);
        jQuery("#eq_type").text(data.typeOfEquipment.type_of_equipment_name);
        jQuery("#eq_sub").text(data.subdivision.subdivision_name);
        jQuery("#eq_inv").text(data.inventoryNumber);
        jQuery("#eq_year").text(data.graduationYear);
        jQuery("#eq_prod").text(data.producerOfEquipment);
        jQuery("#eq_desc").text(data.description);

        current_equipment = equipment_id;
        current_equipment_name = data.equipmentName;
        current_equipment_type = data.typeOfEquipment.type_of_equipment_id;
    });
}

function getTechCard(equipment_id){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/cards/equipment/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        cards = data;
        jQuery("#result").html("<center></br><h5>Оборудование: " + current_equipment_name + "</h5></center><button class=\"btn btn-default\"  type=\"button\" onclick=\"addTechCard()\">Добавить тех.карту</button></br><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Номер тех.карты</th><th>Ответственный</th><th>Дата начала</th><th>Дата окончания</th></tr>\n\
  </thead>\n\
<tbody>\n");
        for(var i =0;i<cards.length;i++) {
            number = i;
            $("#params_t").append("<tr><td onclick=\"openTechCard(cards[number].technological_card_id)\"><span class=\"glyphicon glyphicon-file\"></span></td> <td>" + cards[i].technological_card_number + "</td><td>" + cards[i].responsible_for_delivery.last_name + "</td><td>" + cards[i].start_date + "</td><td>" + cards[i].end_date +"</td></tr>");
        }
    });
}

function getSpare(equipment_id){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/components/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        jQuery("#result").html("<center></br><h5>Оборудование: " + current_equipment_name + "</h5></center><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Номенклатура</th><th>Количество</th><th>Производитель</th><th>Описание</th></tr>\n\
  </thead>\n\
<tbody>");
        for(var i =0;i<data.length;i++) {
            components_id = data[i].component_id;
            $("#params_t").append("<tr  onclick='getRepairBySpare(components_id)'><td>" + data[i].spare.spare_name + "</td><td>" + data[i].amount + "</td><td>" + data[i].spare.spare_producer + "</td><td>" + data[i].spare.description + "</td></tr>");

        }
        jQuery("#result").append("<table class=\"table table-hover\" id=\"repair_t\"></table>");
    });
}

function getRepairBySpare(component_id){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/components/"+component_id +"/equipment/" + current_equipment,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        $("#repair_t").html("<table class=\"table table-hover\" id=\"repair_t\">\n\
        <thead>\n\
          <tr><th>Вид ремонта</th><th>Дата</th></tr>\n\
        </thead>\n\
        <tbody>");
        for(var i =0;i<data.length;i++) {
            var date = new Date(data[i].start_date);
            var month = date.getMonth() + 1;
            var day = date.getDate();
            if(day<10){
                day="0"+day;
            }
            if(month<10){
                month ="0" +month;
            }
            var dtade = day + "-" + month + "-" + date.getFullYear();
            $("#repair_t").append("<tr><td>" + data[i].type_of_maintenance.type_of_maintenance_name + "</td><td>" + dtade + "</td></tr>");
        }
    });
}

function getParams(type_id){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/equipments/parameters/"+type_id,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        jQuery("#result").html("<center><h5></br>Оборудование: " + current_equipment_name + "</h5></center><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>Характеристика</th><th>Значение</th><th>Единица измерения</th></tr>\n\
  </thead>\n\
<tbody>\n");
        for(var i =0;i<data.length;i++) {
            $("#params_t").append("<tr><td>" + data[i].parameter_name + "</td><td>" + data[i].parameter_value +"</td><td>" + data[i].measure.measure_value + "</td></tr>");
        }
    });
}

function workedHours(equipment_id){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/working_hours/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        work_hours = 0;
        if(data.length>=1){
            work_hours = data[data.length-1].value;
        }
        jQuery("#result").html("<center><h5></br>Оборудование: " + current_equipment_name + "</h5></center> <b>Текущая наработка: " + work_hours +  "</b></br></br>\
        <button class=\"btn btn-default\"  type=\"button\" onclick=\"addWorkedHours()\">Добавить наработку</button></br><center>История изменений наработки по текущему образцу оборудования</center><table class=\"table table-hover\" id=\"params_t\"></br></br>\n\
               <div id=\"myfirstchart\" style=\"height: 250px;\"></div> </br>\
<thead>\n\
  <tr><th>Изменил</th><th>Дата изменения</th><th>Значение</th></tr>\n\
  </thead>\n\
<tbody>");

        mas = [];
        for(var i =0;i<=data.length-1;i++) {
            var date = new Date(data[i].date_of_adding);
            var mm = date.getMonth() + 1;
            var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
            var value = data[i].value;
            var t = {year: dtade, value: value};
            mas.push(t);
        }
        console.log(mas);
            config = {
                data: mas,
                xkey: 'year',
                ykeys: ['value'],
                labels: ['Значение'],
                fillOpacity: 0.6,
                hideHover: 'auto',
                behaveLikeLine: true,
                resize: true,
                pointFillColors:['#ffffff'],
                pointStrokeColors: ['black'],
                lineColors:['#6495ED']
            };
        config.element = 'myfirstchart';
        Morris.Area(config);

        if(data.length <1){
            $("#myfirstchart").append("<h4>По данному образцу оборудования не имеется истории показателя наработки</h4>");
        }
        else {
            for (var i = 0; i <= data.length - 1; i++) {
                var date = new Date(data[i].date_of_adding);
                var mm = date.getMonth() + 1;
                var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
                $("#params_t").append("<tr><td>" + data[i].responsible.last_name + "</td><td>" + dtade + "</td><td>" + data[i].value + "</td></tr>");
            }
        }
    });
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/working_hours/year/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        $("#result").append("<h3>Наработка за год: </h3> " + data);
    });
}

function addWorkedHours() {
    jQuery("#new_working_hours").dialog({
            title: "Добавление наработки",
            width:300,
            height: 220,
            resizable:false,
            cache: false,
            modal: true,
            buttons: [
                {
                    text: 'Добавить',
                    click: function() {
                        var value = parseFloat(document.getElementById("work_hour").value);
                        var that = this;
                        $(that).dialog("close");
                        $("#work_hour").val("0.0");
                        $.ajax({
                            type: "POST",
                            url: "/working_hours/add",
                            content: "application/json",
                            dataType: "json",
                            context: $(this),
                            data: {value: value, equipment_id: current_equipment},
                            success: function (returnData) {
                                $(that).dialog("close");
                                //window.location.reload(true);//$('#container').html(returnData);
                            }
                        });
                        history.go(0);
                    }},
                {
                    text: 'Закрыть',
                    click: function() {
                        jQuery("#new_working_hours").dialog('close');
                        $("#work_hour").val("0.0");
                    }}
            ]
        }
    );
}

function addTechCard() {
    jQuery("#new_tech_card").dialog({
            title: "Добавление технологической карты оборудования",
            width:550,
            height: 470,
            resizable: false,
            cache: false,
            modal: true
        }
    );
}

function refreshContent()
{
    history.go(0);
}

function addTechnologicalCard() {
    var type_of_maintenance_id = $("#maintenanceType").val();
    var start_date= $("#datepicker1").val();
    var description = $("#description_tech").val();

    var that = this;
    $.ajax({
        type: "POST",
        url: "/cards/add",
        content: "application/json",
        dataType: "json",
        data: { equipment_id: current_equipment, type_of_maintenance_id : type_of_maintenance_id, start_date : start_date, description : description  },
        success: function(returnData){
            $(that).dialog("close");
        }});
    refreshContent();
}

function openTechCard(techCardId){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/cards/"+techCardId,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        jQuery("#techCardDialog").html("          <div class=\"col-md-4\" style=\"margin: 25px;\">\n\
            <p class=\"form-control-static\"> <b>Номер карты: </b></p>\n\
            <p class=\"form-control-static\"> <b>Оборудование: </b></p>\n\
            <p class=\"form-control-static\"> <b>Тип ремонта: </b></p>\n\
            <p class=\"form-control-static\"> <b>Дата:</b></p>\n\
            <p class=\"form-control-static\"> <b>Ответственный:</b></p>\n\
            <p class=\"form-control-static\"> <b>Описание:</b></p>\n\
          </div>\n\
\n\
          <div id = \"info\" class=\"col-md-3\" style=\"margin: 25px;\" align=\"left\">\n\
            <p class=\"form-control-static\" id=\"tech_card_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"equipment_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"type_of_maintenance_id\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"start_date\"></p>\n\
\n\
            <p class=\"form-control-static\" id=\"responsible_for_delivery\"></p>\n\
            \n\
            <p class=\"form-control-static\" id=\"tech_card_title\"></p>");

        var date = new Date(data.start_date);
        var month = date.getMonth() + 1;
        var day = date.getDate();
        if(day<10){
            day="0"+day;
        }
        if(month<10){
            month ="0" +month;
        }
        var dtade = day + "-" + month + "-" + date.getFullYear();

        desc = data.description;
        jQuery("#tech_card_id").text(data.technological_card_number);
        jQuery("#equipment_id").text(data.equipment.equipmentName);
        jQuery("#type_of_maintenance_id").text(data.type_of_maintenance.type_of_maintenance_name);
        jQuery("#start_date").text(dtade);
        jQuery("#responsible_for_delivery").text(data.responsible_for_delivery.responsible);
        jQuery("#tech_card_title").text(desc);
        jQuery("#techCardDialog").dialog({
            title: "Технологическая карта №"+ data.technological_card_number,
            width:650,
            height: 570,
            resizable:false,
            modal: true,
            buttons: [
                {
                    text: 'Закрыть',
                    click: function() {
                        jQuery("#techCardDialog").dialog('close');
                        refreshContent();
                    }}
            ]
        });
    });
}

function downTime(equipment_id){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/downtime/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        work_hours = 0;
        if(data.length>=1){
            work_hours = data[data.length-1].value;
        }
        jQuery("#result").html("<center><h5></br>Оборудование: " + current_equipment_name + "</h5></center> <b>Текущий простой оборудования: " + work_hours +  "</b></br></br>\
        <button class=\"btn btn-default\"  type=\"button\" onclick=\"addDownTime()\">Добавить простой</button></br><center>История изменений простоя по текущему образцу оборудования</center><table class=\"table table-hover\" id=\"params_t\"></br></br>\n\
               <div id=\"myfirstchart\" style=\"height: 250px;\"></div> </br>\
<thead>\n\
  <tr><th>Изменил</th><th>Дата изменения</th><th>Значение</th></tr>\n\
  </thead>\n\
<tbody>");
        mas = [];
        for(var i =0;i<=data.length-1;i++) {
            var date = new Date(data[i].date_of_adding);
            var mm = date.getMonth() + 1;
            var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
            var value = data[i].value;
            var t = {year: dtade, value: value};
            mas.push(t);
        }
        console.log(mas);
        config = {
            data: mas,
            xkey: 'year',
            ykeys: ['value'],
            labels: ['Значение'],
            fillOpacity: 0.6,
            hideHover: 'auto',
            behaveLikeLine: true,
            resize: true,
            pointFillColors:['#ffffff'],
            pointStrokeColors: ['black'],
            lineColors:['#AB6C6C']
        };
        config.element = 'myfirstchart';
        Morris.Area(config);

        if(data.length <1){
            $("#myfirstchart").append("<h4>По данному образцу оборудования не имеется истории показателя наработки</h4>");
        }
        else {
            for (var i = 0; i <= data.length - 1; i++) {
                var date = new Date(data[i].date_of_adding);
                var mm = date.getMonth() + 1;
                var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
                $("#params_t").append("<tr><td>" + data[i].responsible.last_name + "</td><td>" + dtade + "</td><td>" + data[i].value + "</td></tr>");
            }
        }
    });
}

function addDownTime() {
    jQuery("#new_downtime").dialog({
            title: "Добавление простоя",
            width:350,
            height: 270,
            resizable:false,
            cache: false,
            modal: true,
            buttons: [
                {
                    text: 'Добавить',
                    click: function() {
                        if (parseFloat(document.getElementById("downtime").value) <= 0 || parseFloat(document.getElementById("downtime").value)>100) {
                            $("#errormsg2").text("Неверное значение");
                        }
                        else {
                            var value = parseFloat(document.getElementById("downtime").value);
                            var that = this;
                            $(that).dialog("close");
                            $("#downtime").val("");
                            $.ajax({
                                type: "POST",
                                url: "/downtime/add",
                                content: "application/json",
                                dataType: "json",
                                context: $(this),
                                data: {value: value, equipment_id: current_equipment},
                                success: function (returnData) {
                                    $(that).dialog("close");
                                    //window.location.reload(true);//$('#container').html(returnData);
                                }
                            });
                            SendGet(current_equipment);
                            downTime(current_equipment);
                        }
                        SendGet(current_equipment);
                        downTime(current_equipment);
                    }},
                {
                    text: 'Закрыть',
                    click: function() {
                        jQuery("#new_downtime").dialog('close');
                        $("#downtime").val("");
                    }}
            ]
        }
    );
}

function getDocuments(equipment_id){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/documents/"+equipment_id,
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data) {
        jQuery("#result").html("<center></br><h5>Оборудование: " + current_equipment_name + "</h5></br><button class=\"btn btn-default\"  type=\"button\" onclick=\"addDocuments();\">Добавить документ</button></br></center><table class=\"table table-hover\" id=\"params_t\">\n\
<thead>\n\
  <tr><th>&nbsp;</th><th>Наименование</th><th>Дата добавления</th><th>Описание</th></tr>\n\
  </thead>\n\
<tbody>");
        for(var i =0;i<data.length;i++) {
            components_id = data[i].component_id;
            var date = new Date(data[i].date_of_adding);
            var mm = date.getMonth() + 1;
            var dtade = date.getFullYear() + "-" + 0 + mm + "-" + date.getDate();
            $("#params_t").append("<tr><td onclick=\"openDocuments(this.innerHTML)\">" + data[i].document_id + "</td><td>" + data[i].document_name + "</td><td>" + dtade + "</td><td>" + data[i].description + "</td></tr>");
        }
        jQuery("#result").append("<table class=\"table table-hover\" id=\"repair_t\"></table>");
    });
}

function addDocuments() {
    jQuery("#new_doc").dialog({
            title: "Добавление нового документа",
            width:550,
            height: 470,
            resizable: false,
            cache: false,
            modal: true
        }
    );
}

function getEquipment(){
    $.ajax({
        type: "GET",
        contentType: 'application/json',
        url: "/equipment/all",
        dataType: 'json',
        mimeType: 'application/json',
    }).done(function( data ) {
        jQuery("#result").html("</br><button class=\"btn btn-info\"  type=\"button\" onclick=\"openEquipment()\">Добавить оборудование</button></br><table class=\"table table-hover\" id=\"params_t\">\n\
               </br>\
<thead>\n\
  <tr><th>Инвентарный номер</th><th>Наименование</th><th>Тип</th><th>Цех</th><th>Наработка</th></tr>\n\
  </thead>\n\
<tbody>");


        for(var i =0;i<=data.length-1;i++) {
            $("#params_t").append("<tr><td>" + data[i].inventoryNumber + "</td><td>" + data[i].equipmentName + "</td><td>" + data[i].typeOfEquipment.type_of_equipment_name + "</td><td>" + data[i].subdivision.subdivision_name + "</td><td>" + data[i].workingHours +"</td></tr>");
        }
    });
}

function openEquipment() {
    jQuery("#new_equipment").dialog({
            title: "Добавление оборудования",
            width:500,
            height: 520,
            resizable:false,
            cache: false,
            modal: true
        }
    );
}

function openDocuments(document_id){
    location.href = "/documents/download/"+document_id;
}
