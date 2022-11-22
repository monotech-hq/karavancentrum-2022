
(ns project.router.backend.default-routes
    (:require [http.api               :as http]
              [project.ui.backend.api :as ui]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (function)
;  No method matched
(def METHOD-NOT-ALLOWED #(http/html-wrap {:body (ui/main %) :status 404}))

; @constant (function)
;  Handler returned nil
(def NOT-ACCEPTABLE #(http/html-wrap {:body (ui/main %) :status 404}))

; @constant (function)
;  No route matched
(def NOT-FOUND #(http/html-wrap {:body (ui/main %) :status 200}))
