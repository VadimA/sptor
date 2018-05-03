<head>
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
    <center><h4>Годовой план-график ППР</h4></center>

    <div class="main">
        <div id="dp"></div>
    </div>
    <div class="space">
        Формат:
        <select id="format">
            <option value="svg">SVG</option>
            <option value="png">PNG</option>
        </select>

    </div>
    <div class="space">
        <a href="#" id="print-button">Распечатать</a>
        <a href="#" id="download-button">Загрузить</a>
    </div>

</div>

<script>
    var dp = new DayPilot.Scheduler("dp", {
        locale: "ru-ru",
        cellWidthSpec: "Fixed",
        cellWidth: 30,
        crosshairType: "Header",
        timeHeaders: [{"groupBy":"Month"},{"groupBy":"Day","format":"d"}],
        scale: "Day",
        days: DayPilot.Date.today().daysInYear(),
        startDate: DayPilot.Date.today().firstDayOfYear(),
        showNonBusiness: true,
        businessWeekends: false,
        floatingEvents: true,
        eventHeight: 30,
        eventMovingStartEndEnabled: false,
        eventResizingStartEndEnabled: false,
        timeRangeSelectingStartEndEnabled: false,
        groupConcurrentEvents: false,
        eventStackingLineHeight: 100,
        allowEventOverlap: true,
        timeRangeSelectedHandling: "Enabled",
        onTimeRangeSelected: function (args) {
            var dp = this;
            DayPilot.Modal.prompt("Create a new event:", "Event 1").then(function(modal) {
                dp.clearSelection();
                if (!modal.result) { return; }
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
        eventDeleteHandling: "Update",
        onEventDeleted: function (args) {
            this.message("Event deleted");
        },
        eventClickHandling: "Disabled",
        eventHoverHandling: "Disabled",
        contextMenu: new DayPilot.Menu({
            items: [
                { text: "Delete", onClick: function(args) { var dp = args.source.calendar; dp.events.remove(args.source); } }
            ]
        }),
    });
    var reservations = [];
    function loadReservations() {
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
        reservations = $.getValues();
        console.log("reservations = ", reservations);
    }
    var resources = [];
    function loadResources() {
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
        resources = $.getValues();
        console.log("resources = ", resources);
    }
    loadResources();
    loadReservations();
    dp.resources = resources;
    dp.events.list = reservations;
    dp.init();
    $(document).ready(function() {
        $("#download-button").click(function(ev) {
            ev.preventDefault();
            var area = $("#area").val();
            dp.exportAs("jpeg", {area: area}).download();
        });
    });
    $(document).ready(function() {
        $("#print-button").click(function(ev) {
            ev.preventDefault();
            var format = $("#format").val();
            dp.exportAs(format, { area: "full" } ).print();
        });
    });
</script>

</body>