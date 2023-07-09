function changeUserStatus(checkbox) {
    accountNonBlock = checkbox.checked;
    username = checkbox.getAttribute('data-username');
    url = "/bookshop/admin/users/" + username;
    if (accountNonBlock != true) {
        url += "/active";
    }
    else {
        url += "/block";
    }
    console.log(url);
    fetch(url, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        // body: JSON.stringify(commentRequest)
    }).then(response => {
        if (response != null) {
            if (response.ok) {
                console.log(response)
                checkbox.isChecked = !checkbox.checked
            }
            else {
                throw new Error(response.status);
            }
        }
    }).catch(err => {
        console.log(err);
        alert("Có lỗi xảy ra!");
    })
}

function changeUserRole(checkbox) {
    isChecked = checkbox.checked;
    username = checkbox.getAttribute('data-username');
    url = "/bookshop/admin/users/" + username;
    if (isChecked == true) {
        url += "/give-admin";
    }
    else {
        url += "/remove-admin";
    }
    console.log(url);
    fetch(url, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
        // body: JSON.stringify(commentRequest)
    }).then(response => {
        if (response != null) {
            if (response.ok) {
                console.log(response)
                checkbox.isChecked = !checkbox.checked
            }
            else {
                throw new Error(response.status);
            }
        }
    }).catch(err => {
        console.log(err);
        alert("Có lỗi xảy ra!");
    })
}



jQuery('#btnSearch').on('click', function () {
    var selectedStatus = jQuery("input[name='status']:checked").val();
    var keyword = jQuery("#search").val();
    var pageSize = jQuery("#pageSize").val();
    var url = "/bookshop/admin/users/search?nonBlock=" + selectedStatus;

    if (keyword != "") {
        url += "&keyword=" + keyword;
    }

    if (pageSize != "") {
        url += "&pageSize=" + pageSize;
    }
    console.log(url)
    window.location.href = url;
});