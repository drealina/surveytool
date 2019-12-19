$(document).ready(function () {

    $('.nBtn, .table .eBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text(); //return Neue Umfrage or Umfrage bearbeiten

        if (text === 'Edit') {
            $.get(href, function (survey, active) {
                $('.myForm #id').val(survey.id);
                $('.myForm #name').val(survey.name);
                $('.myForm #description').val(survey.description);
                $('.myForm #active').val(survey.active);
            });
            $('.myForm #exampleModal').modal();
        } else {
            $('.myForm #id').val('');
            $('.myForm #name').val('');
            $('.myForm #description').val('');
            $('.myForm #active').val('');
            $('.myForm #exampleModal').modal();
        }
    });

    $('.table .delBtn').on('click', function (event) {
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });

});