<head>
    <meta charset="utf-8" />
    <title>daypilot-project</title>

    <style type="text/css">
        p, body, td { font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 10pt; }
        body { padding: 0px; margin: 0px; background-color: #ffffff; }
        a { color: #1155a3; }
        .space { margin: 10px 0px 10px 0px; }
        .header { background: #003267; background: linear-gradient(to right, #011329 0%,#00639e 44%,#011329 100%); padding:20px 10px; color: white; box-shadow: 0px 0px 10px 5px rgba(0,0,0,0.75); }
        .header a { color: white; }
        .header h1 a { text-decoration: none; }
        .header h1 { padding: 0px; margin: 0px; }
        .main { padding: 10px }
        .scheduler_default_corner div:nth-of-type(2) {
            display: none !important;
        }
    </style>

    <!-- DayPilot library -->
    <script src="/resources/daypilot-project/js/daypilot/daypilot-all.min.js"></script>

    <!-- Vue.js -->
    <script src="https://unpkg.com/vue"></script>

</head>
<body>

<div class="main">
    <center><h4>Месячный план-график</h4></center>
    <div id="scheduler-app">
        <scheduler id="dp" :config="initConfig" ref="scheduler"></scheduler>
    </div>

</div>

<script>
    Vue.component('scheduler', {
        props: ['id', 'config'],
        template: '<div :id="id"></div>',
        mounted: function() { this.control = new DayPilot.Scheduler(this.id, this.config).init(); }
    });
    var app = new Vue({
        el: '#scheduler-app',
        data: {
            initConfig: {
                timeHeaders: [{"groupBy": "Month"}, {"groupBy": "Day", "format": "d"}],
                scale: "Day",
                days: DayPilot.Date.today().daysInMonth(),
                startDate: DayPilot.Date.today().firstDayOfMonth(),
                timeRangeSelectedHandling: "Enabled",
                eventHeight: 40,
                locale: "ru-ru",
                monthNames: ["Январь", "Февраль", "Март", "Апрель", "Март", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
                onTimeRangeSelected: function (args) {
                    var dp = this;
                    DayPilot.Modal.prompt("Create a new event:", "Event 1").then(function (modal) {
                        dp.clearSelection();
                        if (!modal.result) {
                            return;
                        }
                        dp.events.add(new DayPilot.Event({
                            start: args.start,
                            end: args.end,
                            id: DayPilot.guid(),
                            resource: args.resource,
                            text: modal.result
                        }));
                    });
                },
                eventMoveHandling: "Update",
                onEventMoved: function (args) {
                    this.message("Event moved");
                },
                eventResizeHandling: "Update",
                onEventResized: function (args) {
                    this.message("Event resized");
                },
                eventClickHandling: "Enabled",
                onEventClicked: function (args) {
                    this.message("Event clicked");
                },
                eventHoverHandling: "Disabled",
                treeEnabled: true,
                onBeforeEventRender: function(args) {
                    args.data.barColor = args.data.color;
                    args.data.areas = [
                        { top: 6, right: 2, icon: "icon-triangle-down", visibility: "Hover", action: "ContextMenu", style: "font-size: 12px; background-color: #f9f9f9; border: 1px solid #ccc; padding: 2px 2px 0px 2px; cursor:pointer;"}
                    ];
                },
                contextMenu: new DayPilot.Menu({
                    items: [
                        {
                            text: "Delete",
                            onClick: function(args) {
                                var e = args.source;
                                app.scheduler.events.remove(e);
                                app.scheduler.message("Deleted.");
                            }
                        },
                        {
                            text: "-"
                        },                        {
                            text: "Blue",
                            icon: "icon icon-blue",
                            color: "#1155cc",
                            onClick: function(args) { app.updateColor(args.source, args.item.color); }
                        },
                        {
                            text: "Green",
                            icon: "icon icon-green",
                            color: "#6aa84f",
                            onClick: function(args) { app.updateColor(args.source, args.item.color); }
                        },
                        {
                            text: "Yellow",
                            icon: "icon icon-yellow",
                            color: "#f1c232",
                            onClick: function(args) { app.updateColor(args.source, args.item.color); }
                        },
                        {
                            text: "Red",
                            icon: "icon icon-red",
                            color: "#cc0000",
                            onClick: function(args) { app.updateColor(args.source, args.item.color); }
                        },

                    ]
                })
            }
        },
        computed: {
            // DayPilot.Scheduler object
            // https://api.daypilot.org/daypilot-scheduler-class/
            scheduler: function () {
                return this.$refs.scheduler.control;
            }
        },
        methods: {
            loadReservations: function () {
                // placeholder for an AJAX call
                jQuery.extend({
                    getValues: function() {
                        var result = null;
                        $.ajax({
                            url: "/daypilot/techcard/",
                            type: 'GET',
                            dataType: 'JSON',
                            async: false,
                            success: function(data) {
                                result = data;
                            }
                        });
                        return result;
                    }
                });
                var results = $.getValues();
                console.log("YEAAHH", results);
                this.scheduler.update({events: results});
            },
            loadResources: function () {
                // placeholder for an AJAX call
                jQuery.extend({
                    getValues: function() {
                        var result = null;
                        $.ajax({
                            url: "/daypilot/equipments/",
                            type: 'GET',
                            dataType: 'JSON',
                            async: false,
                            success: function(data) {
                                result = data;
                            }
                        });
                        return result;
                    }
                });
                var results = $.getValues();
                this.scheduler.update({resources: results});
            },
            updateColor: function(e, color) {
                var dp = this.scheduler;
                e.data.color = color;
                dp.events.update(e);
                dp.message("Color updated");
            }
        },
        mounted: function () {
            this.loadResources();
            this.loadReservations();
        }
    });
    </script>

</body>
