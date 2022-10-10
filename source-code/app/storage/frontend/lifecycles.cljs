
(ns app.storage.frontend.lifecycles
    (:require [app.storage.frontend.dictionary :as dictionary]
              [x.app-core.api                  :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:dictionary/add-terms! dictionary/BOOK]})
