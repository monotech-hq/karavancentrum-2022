
;; -- Namespace ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(ns site.frontend.modules.api
  (:require
   [site.frontend.modules.navbar :as navbar]
   [site.frontend.modules.footer :as footer]
   [site.frontend.modules.table :as table]
   [site.frontend.modules.breadcrumbs :as breadcrumbs]))

;; -- Endpoints ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

(def navbar navbar/view)

(def footer footer/view)

(def table  table/view)

(def breadcrumbs breadcrumbs/view)
