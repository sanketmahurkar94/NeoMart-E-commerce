const BASE_URL="http://localhost:8080"

async function loadProducts()
{
    try{
        const response = await fetch(`${BASE_URL}/products`);
        const products= await response.json();
        console.log(products);
        
        let trendingList=document.getElementById("trending-products");
        let clothingList=document.getElementById("clothing-products");
        let electronicsList=document.getElementById("electronics-products");

        trendingList.innerHTML="";
        clothingList.innerHTML="";
        electronicsList.innerHTML="";

        products.forEach((product) => {
            let productCard = `
                    <div class="col-lg-4 col-md-6">
                        <div class="card h-100">
                            <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${product.name}</h5>
                                <p class="card-text">${product.description}</p>
                                <p class="price"><strong>₹${product.price}</strong></p>
                                <button class="btn btn-primary mt-auto"
                                onclick="addToCart(${product.id}, '${product.name}',${product.price},'${product.imageUrl}')">
                                Add to Cart
                                </button>
                            </div>
                        </div>
                    </div>
            `;

            if(product.category==="Clothing")
            {
                clothingList.innerHTML+= productCard;
            }
            else if(product.category==="Electronics")
            {
                electronicsList.innerHTML+= productCard;
            }
            else{
                trendingList.innerHTML+= productCard;
            }
        });

    }
    catch(error)
    {
        console.log("Erorr fetching products:",error);
        
    }
   
}


async function searchProducts() {
  const keyword = document.getElementById("searchInput").value.trim();

  if (!keyword) {
    alert("Please enter a keyword.");
    return;
  }

  try {
    const response = await fetch(`${BASE_URL}/products/search?keyword=${encodeURIComponent(keyword)}`);
    const products = await response.json();

    const container = document.getElementById("searchResults");
    container.innerHTML = "";

    if (products.length === 0) {
      container.innerHTML = "<p>No products found.</p>";
      return;
    }

    // Create cards dynamically
    products.forEach(product => {
      const card = document.createElement("div");
      card.className = "col-md-3 mb-3";

      card.innerHTML = `
        <div class="card h-100">
          <img src="${product.imageUrl}" class="card-img-top" alt="${product.name}" />
          <div class="card-body">
            <h5 class="card-title">${product.name}</h5>
            <p class="card-text">₹${product.price}</p>
            <p class="card-text small text-muted">${product.category}</p>
          </div>
        </div>
      `;

      container.appendChild(card);
    });

  } catch (error) {
    console.error("Error fetching products:", error);
    alert("Something went wrong while searching.");
  }
}
