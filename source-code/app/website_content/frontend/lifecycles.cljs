
(ns app.website-content.frontend.lifecycles
    (:require [app.website-content.frontend.dictionary :as dictionary]
              [x.app-core.api                          :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:dictionary/add-terms! dictionary/BOOK]})
