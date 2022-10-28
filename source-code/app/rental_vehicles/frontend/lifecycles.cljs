
(ns app.rental-vehicles.frontend.lifecycles
    (:require [app.rental-vehicles.frontend.dictionary :as dictionary]
              [x.app-core.api                          :as x.core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(x.core/reg-lifecycles! ::lifecycles
  {:on-app-boot [:dictionary/add-terms! dictionary/BOOK]})
