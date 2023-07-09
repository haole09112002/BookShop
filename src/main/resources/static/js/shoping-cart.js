function addToCart_author() {
    var id = document.getElementById("id").textContent;
    var quantity = document.getElementById("quantity").value;
    fetch('/bookshop/add-to-cart', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item = {
            id: id,
            quantity: quantity
        })
    })
        .then(response => {
            // Xử lý phản hồi từ máy chủ
            // if (response.ok) {
            return response.json();
            // } else {
            //     throw new Error('Yêu cầu POST thất bại.');
            // }
        })
        .then(bookQuantity => {
            // Xử lý dữ liệu trả về từ máy chủ (nếu có)
            if (bookQuantity != -1) {
                alert("Số lượng sản phẩm còn lại: " + bookQuantity);
            } else {
                alert("Thêm vào giỏ hàng thành công");
            }
        })
        .catch(error => {
            // Xử lý lỗi
            console.error(error);
        });

}

function addToCart() {
    var img = document.getElementsByClassName("img-info")[0].src;
    var id = document.getElementById("id").textContent;
    var title = document.getElementById("title").textContent;
    var price = document.getElementById("price").textContent;
    var quantity = document.getElementById("quantity").value;
    var total = parseFloat(price) * parseFloat(quantity);
    stringCartItems = localStorage.getItem("cartItems");
    var cartItems = [];
    newItem = { id, img, title, price, quantity, total };


    fetch('/bookshop/check-quantity?id=' + id).then(function (response) {
        return response.json();
    }).then(function (bookQuantity) {
        console.log(bookQuantity);
        if (quantity <= bookQuantity) {
            if (stringCartItems != null) {
                cartItems = JSON.parse(stringCartItems);
                item = cartItems.find(item => item.id == id);
                if (item != null) {

                    item.quantity = parseInt(quantity) + parseInt(item.quantity);
                    item.total = parseFloat(item.quantity) * parseFloat(item.price);
                } else {
                    item = newItem
                    cartItems.push(item);
                }

            } else {
                cartItems.push(newItem);
            }
            localStorage.setItem("cartItems", JSON.stringify(cartItems))
            alert("Thêm vào giỏ hàng thành công");
        } else {
            alert("Số lượng sản phẩm còn lại: " + bookQuantity);
        }
    }).catch(function (err) {
        console.warn('Something went wrong.', err);
    });




}

function removeItem(id) {

    var cartItems = JSON.parse(localStorage.getItem("cartItems"));

    // Lọc ra các mục khác với ID được chỉ định
    var updatedCartItems = cartItems.filter(function (item) {
        return item.id != id;
    });
    if (updatedCartItems.length == 0 || updatedCartItems == null) {
        alert('adi');
        removeAll();
    }
    else {
        localStorage.setItem("cartItems", JSON.stringify(updatedCartItems));
    }
    loadShopingCart();

}
function removeAll() {
    localStorage.removeItem("cartItems");  //item
}
function loadShopingCart() {

    // this.event.preventDefault();
    // fetch('/bookshop/getcart').then(function (response) {
    //     return response.json();
    // }).then(function (data) {

    //     console.log(data)
    //     if (data == null || data.length == 0) {
    var cartItems = JSON.parse(localStorage.getItem("cartItems"));

    renderCartItems(cartItems);









}

function renderCartItems(cartItems) {
    if (document.getElementById("listCartItems") != null) {
        var html = "";
        if (cartItems != null) {

            cartItems.forEach(item => {
                html += `<div class="row border-top border-bottom">
                <div class="row main align-items-center">
                    <div class="col-1"><input type="checkbox" name="choose" id="`+ item.id + `" onchange="handleCheckboxChange(false)" ></div>
                    <div class="col-2"><img class="img-fluid" src="`+ item.img + `"></div>
                    <div class="col">
                        <div class="row text-muted">`+ item.title + `</div>
                        <div class="row">`+ item.price + `</div>
                    </div>
                    <div class="col">
                        <a href="#" class="decrease" onclick="handleDecreaseBtn(`+ item.id + `)">-</a>
                        <a href="#" class="border quantity-`+ item.id + `">` + item.quantity + `</a>
                        <a href="#" class="increase" onclick="handleIncreaseBtn(`+ item.id + `)">+</a>
                    </div >
            <div class="col">&euro; `+ item.total + `       <a onclick="removeItem(` + item.id + `)">
                <span class="close"> &#10005;</span>
            </a>
            </div>
                </div >
                </div > `;


            }
            );
            document.getElementById("quantity-items").innerHTML = cartItems.length + ` items`;
        }
        else {
            document.getElementById("quantity-items").innerHTML = 0 + ` items`;
            html = "Không có sản phẩm nào trong giỏ hàng. Hãy quay lại để mua sắm!"
        }

        document.getElementById("listCartItems").innerHTML = html;
    }

}

function handleCheckboxChange(isAuthenticated) {
    const checkboxes = document.querySelectorAll('input[name="choose"]:checked');
    const selectedId = Array.from(checkboxes).map(checkbox => checkbox.id);
    var cartItems = [];
    if (isAuthenticated == false) {
        cartItems = JSON.parse(localStorage.getItem("cartItems"));
    }
    else {
        const check = document.querySelectorAll('input[name="choose"]');
        for (item of check) {
            cartItems.push({ id: item.id, quantity: item.value, price: item.getAttribute("data-total") })
        }
    }
    if (cartItems.length != checkboxes.length) {
        // console.log(cartItems.length, selectedId.length)
        checkboxAll = document.getElementById("checkboxAll");
        checkboxAll.checked = false;

    }
    if (cartItems != null) {
        totalPrice = 0;
        // console.log(cartItems);
        for (let i = 0; i < selectedId.length; i++) {
            for (let j = 0; j < cartItems.length; j++) {
                if (selectedId[i] == cartItems[j].id) {
                    totalPrice += parseFloat(cartItems[j].price) * parseFloat(cartItems[j].quantity);
                }
            }
        }
    }
    document.getElementById("totalPrice").innerHTML = totalPrice;
    // console.log(totalPrice);
}
function checkAll(isAuthenticated) {
    checkboxAll = document.getElementById("checkboxAll");
    const checkboxes = document.querySelectorAll('input[name="choose"]');
    if (checkboxAll.checked) {
        checkboxes.forEach(checkbox => checkbox.checked = true);

    } else {
        checkboxes.forEach(checkbox => checkbox.checked = false);
    }
    handleCheckboxChange(isAuthenticated);
}


function handleDecreaseBtn(id) {
    changeQuantityCartItems(id, false);
}

function handleIncreaseBtn(id) {
    changeQuantityCartItems(id, true);
}


function changeQuantityCartItems(id, isInCrease) {

    stringCartItems = localStorage.getItem("cartItems");
    var cartItems = [];
    if (stringCartItems != null) {
        cartItems = JSON.parse(stringCartItems);
        cartItems = cartItems.map(item => {
            if (item.id == id) {

                if (isInCrease == true) {
                    item.quantity = parseInt(item.quantity) + 1;
                }
                else
                    if (item.quantity > 0)
                        item.quantity = parseInt(item.quantity) - 1;
            }
            return item
        });
        localStorage.setItem("cartItems", JSON.stringify(cartItems));
        loadShopingCart();
    }
}


function sendRequest(isAuthenticated) {
    const checkboxes = document.querySelectorAll('input[name="choose"]:checked');
    if (checkboxes != null && checkboxes.length > 0) {
        var cartItemRequests = []
        if (isAuthenticated == false) {
            var cartItems = JSON.parse(localStorage.getItem("cartItems"));

            for (checkedItem of checkboxes) {
                for (storeItem of cartItems) {
                    if (checkedItem.id == storeItem.id) {
                        cartItemRequests.push({ id: storeItem.id, quantity: storeItem.quantity });
                    }
                }
            }
        }

        else {
            for (checkedItem of checkboxes) {
                cartItemRequests.push({ id: checkedItem.id, quantity: checkedItem.value })
            }
        }
        console.log(cartItemRequests)
        var cartItemsJson = JSON.stringify(cartItemRequests);
        var encodedCartItems = encodeURIComponent(cartItemsJson);

        // Tạo URL với query parameter
        var url = '/check?cartItems=' + encodedCartItems;

        // Chuyển hướng trình duyệt đến URL mới
        window.location.href = url;
    }
}