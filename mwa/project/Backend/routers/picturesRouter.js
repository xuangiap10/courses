import { Router } from 'express'
import multer from 'multer'
//import path from 'path'

import { addNewPicture, deletePicture, getPicture } from '../controllers/picturesController.js'

const router = Router({ mergeParams: true });

//const __dirname = path.dirname(new URL(import.meta.url).pathname);

const storage = multer.diskStorage({
    destination: function (req, file, cb) {
        cb(null, './assets/pics');
    },
    filename: function (req, file, cb) {
        //const fileName = `${file.fieldname}-${Date.now()}${path.extname(file.originalname)}`;
        cb(null, file.originalname);
    },
});
const upload = multer({ storage: storage });

router.post('/', upload.any(), addNewPicture); 

router.get('/', getPicture);
router.delete('/', deletePicture);

export default router;