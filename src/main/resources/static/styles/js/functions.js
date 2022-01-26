$('.table.thead-dark').each(function() {
    var val = parseInt( $(this).text(), 1000),
        dd = parseInt( $("#ll").text()1000);
    if (val < dd) {
        $(this).css('background-color', 'red');
    }
});