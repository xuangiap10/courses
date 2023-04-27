import mongoose from "mongoose";
import usersModel from "../models/userModel.js";


export async function getAllItems(req, res, next) {

    try {
        //const page_number = req.query.page ? req.query.page : 1;
        //const page_size = 100;
        const { user_id } = req.params;
        const result = await usersModel.findOne({ _id: user_id },
            { items: 1, _id: 0 });
        res.json({ success: true, data: result.items });
    } catch (error) {

    }
}
export async function getItemById(req, res, next) {

    try {
        const { user_id, item_id } = req.params;
        const result = await usersModel.findOne(
            { _id: user_id, 'items._id': item_id },
            {
                'items.$': 1,
                _id: 0
            }
        );
        res.json({ success: true, data: result.items[0] });
    } catch (error) {
        next(error);
    }
}
export async function addItem(req, res, next) {

    try {
        const { user_id } = req.params;
        const new_item = { ...req.body, postDate: Date.now() };
        const result = await usersModel.updateOne(
            { _id: user_id },
            {
                $push: { items: new_item }
            }
        );
        res.json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}
/*            status: String,
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
*/

export async function updateItemById(req, res, next) {
    try {
        const { user_id, item_id } = req.params;
        const { status: new_status, title: new_title, category: new_category,
            price: new_price, description: new_description, pictures: new_pictures } = req.body;
        const result = await usersModel.updateOne(
            { _id: user_id, 'items._id': item_id },
            {
                $set:
                {
                    'items.$.title': new_title,
                    'items.$.status': new_status,
                    'items.$.category': new_category,
                    'items.$.price': new_price,
                    'items.$.description': new_description,
                    'items.$.pictures': new_pictures
                }
            });
        res.json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}

export async function deleteItemById(req, res, next) {
    try {
        const { user_id, item_id } = req.params;
        console.log(user_id + '---' + item_id);
        const result = await usersModel.updateOne(
            { _id: user_id },
            { $pull: { items: { _id: item_id } } }
        );
        res.json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}

export async function purchaseItem(req, res, next) {
    console.log('purchaseItem');
    try {
        const { user_id } = req.params;
        const { item_id, buyer_id, buyer_fullname, buyer_email } = req.body
        console.log(item_id);
        const purchaseBy = {
            _id: new mongoose.Types.ObjectId(buyer_id),
            email: buyer_email,
            fullname: buyer_fullname,
            date: Date.now()
        };
        console.log(purchaseBy)
        const result = await usersModel.updateOne(
            { _id: user_id, 'items._id': item_id },
            {
                $set: {
                    'items.$.purchasedBy': purchaseBy,
                    'items.$.status': 'Sold'
                }
            }
        );
        console.log(result);
        res.json({ success: true, data: result })
    } catch (error) {
        next(error);
    }
}
export async function getPurchaseItems(req, res, next) {
    console.log('getPurchaseItems');
    try {
        const { user_id } = req.params;
        const pagesize = parseInt(req.query?.pagesize) || 8;
        const page = parseInt(req.query?.page) || 1
        console.log('pagesize' + pagesize)
        console.log('page' + page)
        console.log(user_id);

        const result = await usersModel.aggregate([
            { $unwind: '$items' },
            {
                $match: {
                    'items.purchasedBy._id': new mongoose.Types.ObjectId(user_id),
                    'items.status': 'Sold'
                }
            },
            { $sort: { 'items.purchasedBy.date': -1 } }
        ]);
        console.log(result);
        res.json({ success: true, data: result })
    } catch (error) {
        next(error);
    }
}