import { Router } from 'express'
import { getSearchItems, getSearchItemById } from '../controllers/searchCtr.js'

const router = Router({ mergeParams: true })

router.get('/', getSearchItems)
router.get('/:item_id', getSearchItemById)

export default router