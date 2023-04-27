import mongoose from 'mongoose'

const schema = {
    email: String,
    fullname: String,
    password: String,
    address: {
        street: String,
        city: String,
        state: String,
        zipcode: Number
    },
    phone: String,
    avatar: String,
    createdDate: Date,
    items: [
        {
            status: String,
            title: String,
            category: String,
            postDate: Date,
            price: Number,
            description: String,
            pictures: [String],
            purchasedBy: {
                _id: mongoose.Types.ObjectId,
                email: String,
                fullname: String,
                date: Date
            }
        }
    ]

}

export default mongoose.model('goodsusers', schema)