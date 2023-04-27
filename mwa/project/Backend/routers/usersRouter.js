import { Router } from 'express'
import { login, signup, getAll, deleteById, getById } from '../controllers/usersController.js';
import itemsRoute from './itemsRouter.js'
import profileRouter from './profileRouter.js'
import checkToken from '../middlewares/checkToken.js';
import imageRouter from './imagesRouter.js';

const router = Router();


router.post('/login', login);
router.post('/signup', signup);

router.get('/', getAll);
router.get('/:user_id', getById)
router.delete('/:user_id', deleteById)

//should add checkToken here
router.use('/:user_id/items', checkToken, itemsRoute);
router.use('/:user_id/profile', checkToken, profileRouter);
//router.use('/:user_id/images', checkToken, imageRouter);
//router.use('/:user_id/items', itemsRoute);
//router.use('/:user_id/profile', profileRouter);
router.use('/:user_id/images', imageRouter);


export default router;