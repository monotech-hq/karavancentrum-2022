
(ns app.vehicles.frontend.lifecycles
    (:require [app.vehicles.frontend.dictionary :as dictionary]
              [x.app-core.api                   :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-app-boot [:dictionary/add-terms! dictionary/BOOK]})
