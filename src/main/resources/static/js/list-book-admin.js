function changeBookStatus(checkbox, bookId) {
    var isChecked = checkbox.checked;

    const formData = new FormData();
    formData.append("bookId", bookId);
    formData.append("isBlock", isChecked);

    fetch('/bookshop/admin/books/update-book-status', {
        method: 'POST',
        body: formData
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Error: ' + response.status);
            }
        })
        .then(result => {
            checkbox.isChecked = !checkbox.checked
            // checkbox.val = "BLOCK";
        })
        .catch(error => {
            console.log(error);
        });
}

jQuery('#btnSearch').on('click', function () {
    var selectedStatus = jQuery("input[name='status']:checked").val();
    var keyword = jQuery("#search").val();
    var pageSize = jQuery("#pageSize").val();
    var url = "/bookshop/admin/books?status=" + selectedStatus;

    if (keyword != "") {
        url += "&keyword=" + keyword;
    }

    if (pageSize != "") {
        url += "&pageSize=" + pageSize;
    }

    window.location.href = url;
});