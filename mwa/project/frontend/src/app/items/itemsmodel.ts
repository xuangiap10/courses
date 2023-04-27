
export interface IItem {
    _id: string,
    user_id: string,
    status: string,
    title: string,
    category: string,
    postDate: string,
    price: string,
    description: string,
    pictures: string,

}

export interface IItem2 {
    _id: string,
    user_id: string,
    status: string,
    title: string,
    category: string,
    postDate: string,
    price: string,
    description: string,
    pictures: string[],

}

export interface IBuyer {
    _id: string,
    email: string,
    fullname: string,
    date: string
}

export const initial_item = {

    _id: "",
    user_id: "",
    status: "",
    title: "",
    category: "",
    postDate: "",
    price: "",
    description: "",
    pictures: [],


}