import mongoose from "mongoose";
import userModel from "../models/userModel.js"

export async function getSearchItemById(req, res, next) {
    try {
        const { item_id } = req.params;
        //console.log(item_id)
        const result = await userModel.aggregate([
            { $unwind: '$items' },
            { $match: { 'items._id': new mongoose.Types.ObjectId(item_id) } }
        ])
        console.log(result)
        res.json({ success: true, data: result[0] });
    } catch (e) {
        next(e)
    }
}

export async function getSearchItems(req, res, next) {
    try {
        const { state, city, category, status, sortby, keyword } = req.query
        const pagesize = parseInt(req.query?.pagesize) || 8;
        const page = parseInt(req.query?.page) || 1
        console.log('pagesize' + pagesize)
        console.log('page' + page)
        var query = []
        if (state != 'All') query.push({ $match: { 'address.state': state } })
        if (city != 'All') query.push({ $match: { 'address.city': city } })
        query.push({ $unwind: '$items' })
        if (category != 'All') query.push({ $match: { 'items.category': category } })
        if (status != 'All') query.push({ $match: { 'items.status': status } })
        console.log(keyword)
        if (keyword) query.push({

            $match: {
                'items.title': {
                    $regex: new RegExp('.*' + keyword + '.*', 'i')
                }
            }
        })
        console.log(query)
        query.push({
            $project: {
                fullname: 1,
                address: 1,
                items: 1,
                email: 1,
            }
        })

        const totalCount = await userModel.aggregate([...query, { $count: "count" }]);
        console.log(totalCount)

        if (sortby == 'Newest') query.push({ $sort: { 'items.postDate': -1, 'items.category': 1, 'items.price': 1 } })
        else if (sortby == 'Oldest') query.push({ $sort: { 'items.postDate': 1, 'items.category': 1, 'items.price': 1 } })
        else if (sortby == 'Highest') query.push({ $sort: { 'items.price': -1, 'items.postDate': -1, 'items.category': 1 } })
        else if (sortby == 'Lowest') query.push({ $sort: { 'items.price': 1, 'items.postDate': -1, 'items.category': 1 } })

        query.push({ $skip: (page - 1) * pagesize })
        query.push({ $limit: pagesize })

        const result = await userModel.aggregate(query)
        //console.log('result' + result);
        res.json({ success: true, data: { ...totalCount[0], page: page, list: result } })
    } catch (e) {
        next(e)
    }
}


