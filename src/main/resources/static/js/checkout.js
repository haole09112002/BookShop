// jQuery(function () {
//     // Khi một phần tử được click
//     jQuery('#checkoutBtn').on('click', function () {
//         sendRequestCheckout();
//     });
// });

function removeAll() {
    localStorage.removeItem("cartItems");  //item
}

function removeItem(id) {

    var cartItems = JSON.parse(localStorage.getItem("cartItems"));

    // Lọc ra các mục khác với ID được chỉ định
    var updatedCartItems = cartItems.filter(function (item) {
        return item.id != id;
    });
    if (updatedCartItems.length == 0 || updatedCartItems == null) {
        removeAll();
    }
    else {
        localStorage.setItem("cartItems", JSON.stringify(updatedCartItems));
    }


}

function sendRequestCheckout(isAuthenticated) {
    var invoiceRequest = {};
    invoiceRequest["fullname"] = jQuery("#fullname").val();
    invoiceRequest["email"] = jQuery("#email").val();
    invoiceRequest["phone"] = jQuery("#phone").val();
    invoiceRequest["province"] = jQuery("#province").val();
    invoiceRequest["district"] = jQuery("#district").val();
    invoiceRequest["ward"] = jQuery("#ward").val();
    invoiceRequest["street"] = jQuery("#street").val();
    invoiceRequest["message"] = jQuery("#comment").val();
    cartItems = [];
    // Lặp qua từng hàng có class là "row"
    $('.myCartItem').each(function () {
        var id = $(this).find('#itemId').val();
        var quantity = $(this).find('#itemQuantity').text();
        item = { id, quantity };
        cartItems.push(item);
    });
    invoiceRequest["cartItems"] = cartItems;

    console.log(JSON.stringify(invoiceRequest));


    fetch("http://localhost:8080/bookshop/invoice", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(invoiceRequest)
    })
        .then(response => response.text())
        .then(result => {
            if (result == "true" && isAuthenticated == false) {
                cartItems.forEach(
                    item => { removeItem(item.id) });

                window.location = "/bookshop/invoice/complete"
            }
            else if (isAuthenticated == true) {
                window.location = "/bookshop/invoice/complete"
            }
            else (
                alert("loi xay ra")
            )
            // document.getElementById("main").innerHTML = renderingResult;
        })
        .catch(error => {
            // Xử lý lỗi nếu có
            console.error('Lỗi:', error);
        });

    // console.log(invoiceRequest);
}