import { createReadStream } from 'fs';
import path from 'path';
import data from '../models/userModel.js'
import fs from 'fs';

export async function addNewPicture(req, res, next) {
    try {

        const { user_id, item_id } = req.params;
        let morepictures = [];
        req.files.forEach(file => {
            /*if (!morepictures.find((p) => p === file.filename)) {
                const picture = file.filename;
                morepictures = [...morepictures, picture];
            }*/
            morepictures = [...morepictures, file.filename];
        });
        //console.log(...morepictures);
        const result = await data.updateOne(
            { _id: user_id, 'items._id': item_id },
            { $addToSet: { 'items.$.pictures': [...morepictures] } }
        )
        res.json({ success: true, data: result });
    } catch (e) {
        next(e)
    }
}

export async function getPicture(req, res, next) {
    try {


        const { user_id, item_id } = req.params;
        const result = await data.findOne(
            { _id: user_id, 'items._id': item_id },
            {
                'items.$': 1,
                _id: 0
            }
        );
        const pictures = result.items[0].pictures;
        if (pictures.length > 0) {
            const __dirname = path.dirname(new URL(import.meta.url).pathname);
            const path_name = path.join(__dirname, '../assets/pics', pictures[0]);
            createReadStream(path_name).pipe(res);
        } else {
            next(new Error("Not exist the picture"));
        }
    } catch (e) {
        next(e)
    }
}

export async function deletePicture(req, res, next) {
    try {
        const { user_id, item_id } = req.params;
        const result = await data.updateOne(
            { _id: user_id, 'items._id': item_id },
            { $set: { 'items.$.pictures': [] } }
        )
        res.json({ success: true, data: result });
    } catch (error) {
        next(error);
    }
}
