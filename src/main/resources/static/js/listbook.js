// var request = {
//     keyword: document.getElementById("txtSearch").value,
//     page : 3,
//     maxPrice: 0,
//     minPrice: 1000000,
//     categoryId : 1,
//     sortType: "asc",
//     authorName: ""

// } 
// function getParamater(){
//     keyword = document.getElementById('txtSearch').value;
//     page =    document.getElementById('pagination').getAttribute("data-value");
//     maxPrice = document.getElementById('maxPrice').value;
//     minPrice = document.getElementById('minPrice').value;
//     sort = document.getElementById('dropdownSort').getAttribute("data-value")
//     console.log(keyword + " " + page + " " + maxPrice + " " + minPrice + " " + sort);
//     request = null;
//     if(keyword != null && maxPrice != '' && minPrice != '' && sort != null){
//         request = {
//             keyword, page, maxPrice, minPrice, sort
//         }  
//     }
//     if(keyword == null && maxPrice != '' && minPrice != '' && sort != null){
//         request = {
//             page, maxPrice, minPrice, sort
//         }  
//     }
//     if(keyword == null && maxPrice != '' && minPrice != '' && sort == null){
//         request = {
//             page, maxPrice, minPrice
//         }  
//     }
//     if(keyword == null && maxPrice == '' && minPrice == '' && sort == null){
//         request = {
//             page
//         }  
//     }
//     if(keyword != null && maxPrice == '' && minPrice == '' && sort == null){
//         request = {
//             keyword
//         }  
//     }
//     if(keyword != null && maxPrice == '' && minPrice == '' && sort == null){
//         request = {
//             page, keyword
//         }  
//     }
//     if(keyword == null && maxPrice == '' && minPrice == '' && sort != null){
//         request = {
//             page, sort
//         }  
//     }
//     if(keyword != null && maxPrice == '' && minPrice == '' && sort != null){
//         request = {
//             page, keyword, sort
//         }  
//     }
//     return request;
// }

function requestListBook() {
    alert("chào");
    console.log(getParamater());


    request = getParamater();
    axios.get('/bookshop/books/search', {
        params: request
    })
        .then(function (response) {

        })
        .catch(function (error) {
            // Xử lý lỗi (nếu có)
            console.log(error);
        });
}

