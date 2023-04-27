import { createReadStream } from 'fs';
import path from 'path';
import fs from 'fs';

export async function addNewImage(req, res, next) {
    try {

        let morepictures = [];
        req.files.forEach(file => {
            morepictures = [...morepictures, file.filename];
        });
        if (morepictures.length > 0)
            res.json({ success: true, data: morepictures[0] });
        else {
            res.json({ success: false, data: "no file uploaded" });
        }
    } catch (e) {
        next(e)
    }
}

export async function getImage(req, res, next) {
    try {


        const { filename } = req.body;
        if (filename) {
            const __dirname = path.dirname(new URL(import.meta.url).pathname);
            const path_name = path.join(__dirname, '../assets/pics', filename);
            createReadStream(path_name).pipe(result => res.json({ success: true, data: result }));
        } else {
            next(new Error("Not exist the picture"));
        }
    } catch (e) {
        next(e)
    }
}

export async function deleteImage(req, res, next) {
    try {
        const { filename } = req.body;
        if (filename) {
            const __dirname = path.dirname(new URL(import.meta.url).pathname);
            const path_name = path.join(__dirname, '../assets/pics', filename);
            fs.unlink(path_name, (err) => {
                if (err) {
                    next(err);
                } else {
                    res.json({ success: true, data: filename });
                }
            })
        } else {
            next(new Error("Not exist the picture"));
        }


    } catch (error) {
        next(error);
    }
}
