$(function () {
    var caseCount = 0;

    $.showAddCaseForm = function () {
        $('#add-form').css('display', 'flex');
        $('#fog').css('display', 'flex');
    };

    $('#fog').click(function (e) {
        $('#add-form').css('display', 'none');
        $('#update-form').css('display', 'none');
        $('#fog').css('display', 'none');
    });

    //Вывод в таблицу
    $.appendCase = function(date) {
        caseCount++;
        var caseRow = '<tr>'
            + '<td class="id">' + caseCount + '</td>'
            + '<td class="case">' + date.value + '</td>'
            + '<td class="dead-line">' + date.deadLine + '</td>'
            + '<td class="delete"><button class="button-delete" onclick="$.deleteCase(' + date.id + ')">Удалить</button></td>'
            + '<td class="update"><button class="button-update" onclick="$.updateCase(' + date.id + ')">Обновить</button></td>'
            + '</tr>';
        $('#case-list-table').append(caseRow);
    }

    //Обновление таблицы
    $.updateTable = function() {
        caseCount = 0;
        $('#case-list-table tr').each(function () {
           $(this).remove();
        });
        $('#case-list-table').append($('<tr id="header">'
            + '<td class="id">№</td>'
            + '<td class="case">Дело</td>'
            + '<td class="dead-line">Срок</td>'
            + '<td class="delete"><button onclick="$.clear()">Удалить всё</button></td>'
            + '<td class="update"><button class="button-update" onclick="$.showAddCaseForm()">Создать дело</button></td>'
            + '</tr>'));
        $.fillTable();
    }

    //Удаление по id
    $.deleteCase = function(id) {
        $.ajax({
            method: "DELETE",
            url: ('/api/v2/case/' + id),
            success: function () {
                $.updateTable();
            }
        });
    }

    //Получение от бэка
    $.fillTable = function() {
        $.ajax({
            method: "GET",
            url: '/api/v2/case/',
            success: function (response) {
                var i = 1;
                response.forEach(function (newCase) {
                    console.log(newCase);
                    $.appendCase(newCase);
                });
            }
        });
    }

    //Добовление в БД
    $('#add-case').click(function () {
        var data = $('#add-form form').serialize();
        $.ajax({
            method: "POST",
            url: '/api/v2/case/',
            data: data,
            success: function () {
                $.updateTable();
                $('#add-form').css('display', 'none');
                $('#fog').css('display', 'none');
            }
        });
    });

    //Отчистка
    $.clear = function () {
        $.ajax({
            method: "DELETE",
            url: '/api/v2/case/',
            success: function () {
                $.updateTable();
            }
        });
    }

    //Обновление по id
    $.updateCase = function (id) {
        $('#update-form').css('display', 'flex');
        $('#fog').css('display', 'flex');

        $('#update-case').click(function () {
            var data = $('#update-form form').serialize();
            $.ajax({
                method: "PUT",
                url: ('/api/v2/case/' + id),
                data: data,
                success: function () {
                    $('#update-form').css('display', 'none');
                    $('#fog').css('display', 'none');
                    $.updateTable();
                }
            })
        })
    }
});