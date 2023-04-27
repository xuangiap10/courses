import { Router } from 'express'
import { addItem, deleteItemById, getAllItems, getItemById, updateItemById, purchaseItem, getPurchaseItems } from '../controllers/itemsController.js';

import picturesRoute from './picturesRouter.js'

const router = Router({ mergeParams: true });

router.get('', getAllItems);
router.post('', addItem);
router.patch('/purchase', purchaseItem)
router.get('/purchaselist', getPurchaseItems);
router.patch('/:item_id', updateItemById);
router.delete('/:item_id', deleteItemById);
router.get('/:item_id', getItemById);

router.use('/:item_id/pictures', picturesRoute);

export default router;