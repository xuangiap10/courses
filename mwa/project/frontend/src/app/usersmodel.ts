export interface IUser {
    _id: string,
    fullname: string,
    email: string,
    password: string
}

export interface Address {
    street: string,
    city: string,
    state: string,
    zipcode: number
}

export interface SignUpUser {
    fullname: string,
    email: string,
    password: string,
    address: Address,
    phone: string,
    avatar: string
}

export interface IProfile {
    fullname: string,
    address: Address,
    phone: string,
    avatar: string
}

export const initial_addr = {
    street: "",
    city: "",
    state: "",
    zipcode: 0
}

export interface ISearch {
    state: string,
    city: string,
    category: string,
    status: string,
    sortby: string,
    pagesize: number,
    keyword: string,
    page: number
}
export interface ISearchItem {
    _id: string,
    status: string,
    title: string,
    category: string,
    postDate: string,
    price: Number,
    description: string,
    purchasedBy: {
        _id: string,
        fullname: string,
        email: string,
        date: string
    }
    pictures: string[]
}
export interface ISearchItemFull {
    _id: string,
    fullname: string,
    address: Address,
    email: string,
    items: ISearchItem
}

export interface ISearchItemRes {
    count: number,
    page: number,
    list: ISearchItemFull[]
}
export interface IPurchaseItem {
    item_id: string,
    seller_id: string,
    buyer_id: string,
    buyer_fullname: string,
    buyer_email: string
}

export const states = [
    { state: 'All', city: ['All'] },
    { state: 'Illinois', city: ['All', 'Aurora', 'Chicago', 'Joliet', 'Naperville', 'Rockford'] },
    { state: 'Iowa', city: ['All', 'Cedar Rapids', 'Davenport', 'Des Moines', 'Fairfield', 'Sioux City', 'Waterloo'] },
    { state: 'Ohio', city: ['All', 'Cincinnati', 'Cleveland', 'Columbus', 'Dayton', 'Toledo'] },
    { state: 'Indiana', city: ['All', 'Bloomington', 'Evansville', 'Fort Wayne', 'Indianapolis', 'South Bend'] },
    { state: 'Texas ', city: ['All', 'Austin', 'Dallas', 'Houston', 'San Antonio', 'Fort Worth'] }
]

export const category = [
    'All',
    'Clothes',
    'Electronic',
    'Car',
    'Food'
]

export const sortby = [
    'Newest',
    'Oldest',
    'Highest',
    'Lowest'
]

export const status = ['All', 'Active', 'Sold']