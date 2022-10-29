
(ns site.components.frontend.scroll-icon.helpers
    (:require [x.app-environment.api :as x.environment]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn scroll-f
  ; @param (boolean) intersecting?
  [intersecting?]
  (x.environment/set-element-attribute! "sc-scroll-icon" "data-visible" intersecting?))
